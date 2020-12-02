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
package com.jcuesoft.cuebridge.link.service.a1;

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

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;

import com.jcuesoft.cuebridge.link.util.UtilJPA;
import com.jcuesoft.cuebridge.link.util.UtilProperties;


public class LinkA1SyncScreenServerHandler extends SimpleChannelInboundHandler<Object> {

    private HttpRequest request;
    public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    public static final int HTTP_CACHE_SECONDS = 1;
    static UtilProperties util;
    String uri;
    String domain;
    String hostName;
    EntityManager em;
    Map<String, String> paramMap;
    String callback;
    LinkA1SyncScreenService service;
    
    /** Buffer that stores the response content */
    private final StringBuilder buf = new StringBuilder();
    
    public LinkA1SyncScreenServerHandler() throws Exception{
        System.out.println("LinkA1SyncScreenServerHandler init");
        callback = "Success";
        util = new UtilProperties();
        service = new LinkA1SyncScreenService();
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
            callback = "Success";
            HttpRequest request = this.request = (HttpRequest) msg;

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
            
            /*==================================================================================
             * syncscreen service get method start
            ==================================================================================*/                
            System.out.println("#syncscreen service");
            
            if (is100ContinueExpected(request)) {
                send100Continue(ctx);
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

            //QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(request.getUri(), "EUC-KR"));
            Map<String, List<String>> params = queryStringDecoder.parameters();
            if (!params.isEmpty()) {
                for (Entry<String, List<String>> p: params.entrySet()) {
                    String key = p.getKey();
                    List<String> vals = p.getValue();
                    for (String val : vals) {
                        buf.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
                        System.out.println("key:val-"+key+":"+val);
                        if(key.equals("status")){
                            if(val.equals("01")){
                                val = "접수";
                            }else if(val.equals("02")){
                                val = "심사";
                            }else if(val.equals("03")){
                                val = "1차결재";
                            }else if(val.equals("04")){
                                val = "고객확인";
                            }else if(val.equals("05")){
                                val = "2차결재";
                            }else if(val.equals("06")){
                                val = "대출대상";
                            }else if(val.equals("07")){
                                val = "대출";
                            }else if(val.equals("08")){
                                val = "보류";
                            }else if(val.equals("09")){
                                val = "거절";
                            }else if(val.equals("10")){
                                val = "휴지통";
                            }else if(val.equals("11")){
                                val = "본인인증미완료";
                            }else if(val.equals("12")){
                                val = "본인인증보류";
                            }else if(val.equals("13")){
                                val = "본인인증확인";
                            }else{
                                val = " ";
                            }
                        }
                        paramMap.put(key, val);                            
                    }
                }
                buf.append("\r\n");         
            }

            callback = checkValidParam(paramMap);
            if(!callback.substring(0,1).equals("F")){
                em = UtilJPA.getentityManagerFactory(domain).createEntityManager();
                callback = service.excuteBiz(em, paramMap);
                buf.append(callback);
                buf.append("\r\n");                    
            }
            
            appendDecoderResult(buf, request);
            /*==================================================================================
             * syncscreen service get method end
            ==================================================================================*/            
        }
        
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            
            ByteBuf content = httpContent.content();
            if (content.isReadable()) {
                buf.append("CONTENT: ");
                buf.append(content.toString(CharsetUtil.UTF_8));
                buf.append("\r\n");
                appendDecoderResult(buf, request);                
            }else{
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


    private String checkValidParam(Map<String, String> param) {
        System.out.println("checkValidPatam");
        if(param.get("code") == null || param.get("code").equals("")){
            callback = "Fail: code/거래처접수식별번호 필수입력항목입니다";
            buf.append("ERROR FEDDBACK : " + callback);
            buf.append("\r\n");
        }
        if(param.get("status") == null || param.get("status").equals("")){
            callback = "Fail: status/심사상태코드명 필수입력항목입니다";
            buf.append("ERROR FEDDBACK : " + callback);
            buf.append("\r\n");
        }        
        
        System.out.println("checkValidParam callback:"+callback);
        return callback;
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
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, currentObj.getDecoderResult().isSuccess()? OK : BAD_REQUEST,
                Unpooled.copiedBuffer(buf.toString(), CharsetUtil.UTF_8));

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
