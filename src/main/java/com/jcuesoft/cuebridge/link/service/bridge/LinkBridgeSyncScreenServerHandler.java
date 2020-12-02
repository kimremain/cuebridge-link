/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.jcuesoft.cuebridge.link.service.bridge;

import static io.netty.handler.codec.http.HttpHeaders.getHost;
import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.COOKIE;
import static io.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;

import com.google.gson.Gson;
import com.jcuesoft.cuebridge.link.module.ModuleCommon;
import com.jcuesoft.cuebridge.link.util.UtilJPA;
import com.jcuesoft.cuebridge.link.util.UtilProperties;


public class LinkBridgeSyncScreenServerHandler extends SimpleChannelInboundHandler<Object> {

    private HttpRequest request;
    static UtilProperties util;
    static ModuleCommon mc;
    static LinkBridgeSyncScreenService service;
    String uri;
    String domain;
    String hostName;
    EntityManager em;
    Map<String, String> paramMap;
    String cdResult;
    String dsResult;
    String noEx;
    
    /** Buffer that stores the response content */
    private final StringBuilder buf = new StringBuilder();
    public LinkBridgeSyncScreenServerHandler() throws Exception{
        System.out.println("LinkBridgeSyncScreenServerHandler init");
        cdResult = "S";
        dsResult = "";
        noEx = "";
        util = new UtilProperties();
        mc = new ModuleCommon();
        service = new LinkBridgeSyncScreenService();
        paramMap = new HashMap<String, String>();
        
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();        
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        
        if (msg instanceof HttpRequest) {
            System.out.println(" ");
            System.out.println("<Event> msg instanceof HttpRequest:"+new Date());
            cdResult = "S";
            dsResult = "";
            HttpRequest request = this.request = (HttpRequest) msg;
            
            if (is100ContinueExpected(request)) {
                send100Continue(ctx);
            }            

            hostName = getHost(request, "unknown");
            uri = request.getUri();            
            System.out.println("request hostname:"+hostName);
            System.out.println("request uri:"+uri);
            
            domain = hostName;
            if(hostName.equals("unknown")){
                domain = uri;
            }
            if(domain.equals("unknown")){
                System.err.println("<Error> can't find match host");
            }
            
            buf.setLength(0);
            buf.append("WELCOME TO THE CUEBRIDGE HTTP SYNC SERVER\r\n");
            buf.append("===================================\r\n");

            buf.append("VERSION: ").append(request.getProtocolVersion()).append("\r\n");
            buf.append("HOSTNAME: ").append(getHost(request, "unknown")).append("\r\n");
            buf.append("REQUEST_URI: ").append(request.getUri()).append("\r\n\r\n");

            HttpHeaders headers = request.headers();
            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> h: headers) {
                    String key = h.getKey();
                    String value = h.getValue();
                    buf.append("HEADER: ").append(key).append(" = ").append(value).append("\r\n");
                }
                buf.append("\r\n");
            }

            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
            Map<String, List<String>> params = queryStringDecoder.parameters();
            if (!params.isEmpty()) {
                for (Entry<String, List<String>> p: params.entrySet()) {
                    String key = p.getKey();
                    List<String> vals = p.getValue();
                    for (String val : vals) {
                        buf.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
                    }
                }
                buf.append("\r\n");
            }
            appendDecoderResult(buf, request);
        }
        
        if (msg instanceof HttpContent) {
            System.out.println("<Event> msg instanceof HttpContent:"+new Date());
            HttpContent httpContent = (HttpContent) msg;
            
            ByteBuf content = httpContent.content();
            if (content.isReadable()) {
                buf.append("CONTENT: ");
                buf.append(content.toString(CharsetUtil.UTF_8));
                buf.append("\r\n");
                appendDecoderResult(buf, request);
                paramMap = ModuleCommon.convertStrToMap(java.net.URLDecoder.decode(content.toString(CharsetUtil.UTF_8), "UTF-8"));
                System.out.println("paramMap:"+paramMap);
                em = UtilJPA.getentityManagerFactory(domain).createEntityManager();

                if(uri.matches("(.*)syncscreen(.*)")){
                    /*==================================================================================
                     * LinkBridgeSyncScreenService post method start
                    ==================================================================================*/
                    cdResult = checkValidParam(paramMap);
                    System.out.println(cdResult);
                    if(!cdResult.substring(0,1).equals("F")){
                        //LinkYSMGroupService service = new LinkYSMGroupService();
                        cdResult = service.excuteBiz(em, paramMap);
                        if(cdResult.substring(0,1).equals("F")){
                            dsResult = cdResult;
                            cdResult = "F";
                        }else{
                            noEx = cdResult;
                            cdResult = "S";
                        }
                        buf.append(cdResult);
                        buf.append("\r\n");
                    }
                    /*==================================================================================
                     * LinkBridgeSyncScreenService post method end
                    ==================================================================================*/                    
                }else{
                    cdResult = "F";
                    dsResult = dsResult + "unknow service url:"+domain;
                    System.err.println("<Notice> unknow service url:"+domain);
                }

            }else{
                cdResult = "F";
                dsResult = dsResult + "unreadable contents";                
                System.err.println("<Notice> unreadable contents");
            }

            if (msg instanceof LastHttpContent) {
                System.out.println("<Event> msg instanceof LastHttpContent:"+new Date());
                buf.append("END OF CONTENT\r\n");
                LastHttpContent trailer = (LastHttpContent) msg;
                if (!trailer.trailingHeaders().isEmpty()) {
                    buf.append("\r\n");
                    for (String name: trailer.trailingHeaders().names()) {
                        for (String value: trailer.trailingHeaders().getAll(name)) {
                            buf.append("TRAILING HEADER: ");
                            buf.append(name).append(" = ").append(value).append("\r\n");
                        }
                    }
                    buf.append("\r\n");
                }

                if (!writeResponse(trailer, ctx)) {
                    // If keep-alive is off, close the connection once the content is fully written.
                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                }
            }
        }        

    }
    
    private String checkValidParam(Map<String, String> param) throws Exception {
        System.out.println("checkValidParamScreen init");
        /*
         * 필수항목 null 체크
        */
        dsResult = ModuleCommon.isNulltoMessage(param.get("no_req"), "no_req");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("cd_cooperate_partner"), "cd_cooperate_partner");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("cd_cooperate"), "cd_cooperate");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("nm_screen"), "nm_screen");

        // 숫자타입 검증
        if(ModuleCommon.isNumeric(param.get("am_cooperate"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"am_cooperate parameter is not numeric:"+param.get("am_cooperate");
        }
        if(ModuleCommon.isNumeric(param.get("am_cooperate_expect"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"am_cooperate_expect parameter is not numeric:"+param.get("am_cooperate_expect");
        }

        if(!dsResult.equals("")) cdResult = "F";
        
        System.out.println("checkValidParam cdResult:"+cdResult);
        System.out.println("checkValidParam dsResult:"+dsResult);
        return cdResult;
    }

    private static void appendDecoderResult(StringBuilder buf, HttpObject o) {
        DecoderResult result = o.getDecoderResult();
        if (result.isSuccess()) {
            return;
        }
        buf.append(".. WITH DECODER FAILURE: ");
        buf.append(result.cause());
        buf.append("\r\n");
    }

    private boolean writeResponse(HttpObject currentObj, ChannelHandlerContext ctx) {
        // Decide whether to close the connection or not.
        boolean keepAlive = isKeepAlive(request);
        // Build the response object.
        System.out.println("writeResponse original buf:"+buf.toString());
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("cd_result", cdResult);
        resultMap.put("ds_result", dsResult);

        Gson gson = new Gson(); 
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, currentObj.getDecoderResult().isSuccess()? OK : BAD_REQUEST,
                Unpooled.copiedBuffer(gson.toJson(resultMap), CharsetUtil.UTF_8));
        System.out.println("writeResponse json buf:"+gson.toJson(resultMap));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        // Encode the cookie.
        String cookieString = request.headers().get(COOKIE);
        if (cookieString != null) {
            Set<Cookie> cookies = CookieDecoder.decode(cookieString);
            if (!cookies.isEmpty()) {
                // Reset the cookies if necessary.
                for (Cookie cookie: cookies) {
                    response.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
                }
            }
        } else {
            // Browser sent no cookie.  Add some.
            response.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key1", "value1"));
            response.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key2", "value2"));
        }

        // Write the response.
        ctx.write(response);

        return keepAlive;
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
        ctx.write(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {     
        cause.printStackTrace();
        ctx.close();
    }

}
