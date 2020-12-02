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
package com.jcuesoft.cuebridge.link.service.recruit;

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


public class LinkRecruitServerHandler extends SimpleChannelInboundHandler<Object> {

    private HttpRequest request;
    static UtilProperties util;
    static ModuleCommon mc;
    static LinkRecruitService service;
    String uri;
    String domain;
    String hostName;
    EntityManager em;
    Map<String, String> paramMap;
    String cdResult;
    String dsResult;
    String noReq;
    
    /** Buffer that stores the response content */
    private final StringBuilder buf = new StringBuilder();
    public LinkRecruitServerHandler() throws Exception{
        System.out.println("LinkRecruitServerHandler init");
        cdResult = "S";
        dsResult = "";
        noReq = "";
        util = new UtilProperties();
        mc = new ModuleCommon();
        service = new LinkRecruitService();
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

                if(uri.matches("(.*)recruit(.*)")){
                    /*==================================================================================
                     * LinkRecruitService service post method start
                    ==================================================================================*/
                    cdResult = checkValidParam(paramMap);
                    System.out.println(cdResult);
                    if(!cdResult.substring(0,1).equals("F")){
                        //LinkYSMGroupService service = new LinkYSMGroupService();
                        cdResult = service.excuteBizRecruit(em, paramMap);
                        if(cdResult.substring(0,1).equals("F")){
                            dsResult = cdResult;
                            cdResult = "F";
                        }else{
                            noReq = cdResult;
                            cdResult = "S";
                        }
                        buf.append(cdResult);
                        buf.append("\r\n");
                    }
                    /*==================================================================================
                     * LinkRecruitService service post method end
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
        dsResult = ModuleCommon.isNulltoMessage(param.get("cd_cooperate"), "cd_cooperate");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("no_cooperate"), "no_cooperate");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("cd_path"), "cd_path");
        //dsResult = dsResult+mc.isNulltoMessage(param.get("cd_pdt"), "cd_pdt");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("nm_custom"), "nm_custom");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("no_tel1_mobile"), "no_tel1_mobile");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("no_tel2_mobile"), "no_tel2_mobile");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("no_tel3_mobile"), "no_tel3_mobile");
        dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("yn_surety"), "yn_surety");

        if(param.get("yn_surety")!=null && param.get("yn_surety").equals("Y")){
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("s_nm_custom"), "s_nm_custom");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("s_no_tel1_mobile"), "s_no_tel1_mobile");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("s_no_tel2_mobile"), "s_no_tel2_mobile");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("s_no_tel3_mobile"), "s_no_tel3_mobile");
        }

        // 숫자타입 검증
        if(ModuleCommon.isNumeric(param.get("am_custom"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"am_custom parameter is not numeric:"+param.get("am_custom");
        }
        if(ModuleCommon.isNumeric(param.get("am_pay_month"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"am_pay_month parameter is not numeric:"+param.get("am_pay_month");
        }
        if(ModuleCommon.isNumeric(param.get("am_deposit_home"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"am_deposit_home parameter is not numeric:"+param.get("am_deposit_home");
        }
        if(ModuleCommon.isNumeric(param.get("am_rent_home"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"am_rent_home parameter is not numeric:"+param.get("am_rent_home");
        }
        if(ModuleCommon.isNumeric(param.get("s_am_pay_month"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_am_pay_month parameter is not numeric:"+param.get("s_am_pay_month");
        }
        if(ModuleCommon.isNumeric(param.get("s_am_deposit_home"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_am_deposit_home parameter is not numeric:"+param.get("s_am_deposit_home");
        }
        if(ModuleCommon.isNumeric(param.get("s_am_rent_home"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_am_rent_home parameter is not numeric:"+param.get("s_am_rent_home");
        }
        
        //YN 검증
        if(ModuleCommon.isYN(param.get("yn_surety"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"yn_surety parameter is not valid:"+param.get("yn_surety");
        }
        if(ModuleCommon.isYN(param.get("yn_nice_safekey"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"yn_nice_safekey parameter is not valid:"+param.get("yn_nice_safekey");
        }
        if(ModuleCommon.isYN(param.get("s_s_yn_nice_safekey"), true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_s_yn_nice_safekey parameter is not valid:"+param.get("s_s_yn_nice_safekey");
        }
        
        //유효코드 검증

        if(ModuleCommon.isCdLike(param.get("cd_path"), "704", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_path parameter is not valid:"+param.get("cd_path");
        }
        System.out.println("???:"+ModuleCommon.isCdLike(param.get("cd_use"), "704", true));        
        if(ModuleCommon.isCdLike(param.get("cd_use"), "206", true)==false){
            System.out.println("왜여길타");
            cdResult = "F";
            dsResult = dsResult+"cd_use parameter is not valid:"+param.get("cd_use");
        }
        if(ModuleCommon.isCdLike(param.get("cd_echanel"), "122", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_echanel parameter is not valid:"+param.get("cd_echanel");
        }
        if(ModuleCommon.isCdLike(param.get("cd_eportal"), "705", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_eportal parameter is not valid:"+param.get("cd_eportal");
        }
        if(ModuleCommon.isCdLike(param.get("cd_epath"), "706", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_epath parameter is not valid:"+param.get("cd_epath");
        }
        if(ModuleCommon.isCdLike(param.get("cd_info_collect"), "716", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_info_collect parameter is not valid:"+param.get("cd_info_collect");
        }
        if(ModuleCommon.isCdLike(param.get("cd_contact_cust"), "714", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_contact_cust parameter is not valid:"+param.get("cd_contact_cust");
        }
        if(ModuleCommon.isCdLike(param.get("cd_contact_agent"), "718", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_contact_agent parameter is not valid:"+param.get("cd_contact_agent");
        }
        if(ModuleCommon.isCdLike(param.get("cd_call_agent"), "717", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_call_agent parameter is not valid:"+param.get("cd_call_agent");
        }
        if(ModuleCommon.isCdLike(param.get("cd_bank"), "320", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_bank parameter is not valid:"+param.get("cd_bank");
        }
        if(ModuleCommon.isCdLike(param.get("cd_marry"), "707", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_marry parameter is not valid:"+param.get("cd_marry");
        }
        if(ModuleCommon.isCdLike(param.get("cd_knd_mobile"), "708", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_knd_mobile parameter is not valid:"+param.get("cd_knd_mobile");
        }
        if(ModuleCommon.isCdLike(param.get("cd_job_kind"), "709", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_job_kind parameter is not valid:"+param.get("cd_job_kind");
        }
        if(ModuleCommon.isCdLike(param.get("cd_rank"), "133", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_rank parameter is not valid:"+param.get("cd_rank");
        }
        if(ModuleCommon.isCdLike(param.get("cd_job_act"), "186", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_job_act parameter is not valid:"+param.get("cd_job_act");
        }
        if(ModuleCommon.isCdLike(param.get("cd_job_status"), "185", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_job_status parameter is not valid:"+param.get("cd_job_status");
        }
        if(ModuleCommon.isCdLike(param.get("cd_recruit_group"), "719", true)==false){
            cdResult = "F";
            dsResult = dsResult+"cd_recruit_group parameter is not valid:"+param.get("cd_recruit_group");
        }        
        if(ModuleCommon.isCdLike(param.get("s_cd_marry"), "707", true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_cd_marry parameter is not valid:"+param.get("s_cd_marry");
        }
        if(ModuleCommon.isCdLike(param.get("s_cd_knd_mobile"), "708", true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_cd_knd_mobile parameter is not valid:"+param.get("s_cd_knd_mobile");
        }
        if(ModuleCommon.isCdLike(param.get("s_cd_job_kind"), "709", true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_cd_job_kind parameter is not valid:"+param.get("s_cd_job_kind");
        }
        if(ModuleCommon.isCdLike(param.get("s_cd_rank"), "133", true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_cd_rank parameter is not valid:"+param.get("s_cd_rank");
        }
        if(ModuleCommon.isCdLike(param.get("s_cd_job_act"), "186", true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_cd_job_act parameter is not valid:"+param.get("s_cd_job_act");
        }
        if(ModuleCommon.isCdLike(param.get("s_cd_job_status"), "185", true)==false){
            cdResult = "F";
            dsResult = dsResult+"s_cd_job_status parameter is not valid:"+param.get("s_cd_job_status");
        }
        
        if(param.get("cd_cooperate").equals("70301")||param.get("cd_cooperate").equals("70302")||param.get("cd_cooperate").equals("70303")||param.get("cd_cooperate").equals("70316")){
            /*==========================================================================*/
            /*YSM계열사 자체모집의 경우 중개경로/적법수집 skip
             * 20170424 오케이포스 70316 추가
             */
            /*==========================================================================*/  
            
        }else{        
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("no_supply"), "no_supply");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("dt_supply"), "dt_supply");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("nm_supply"), "nm_supply");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("no_supply_loan"), "no_supply_loan");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("no_supply_society"), "no_supply_society");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("no_supply_tel"), "no_supply_tel");            
            
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("cd_info_collect"), "cd_info_collect");
            if(param.get("cd_info_collect")!=null && param.get("cd_info_collect").equals("71601")){
                dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("cd_contact_cust"), "cd_contact_cust");
                if(param.get("cd_contact_cust").equals("71499")){
                    dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("ds_contact_cust"), "ds_contact_cust");
                }
            }
            if(param.get("cd_info_collect")!=null && param.get("cd_info_collect").equals("71602")){
                dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("cd_contact_agent"), "cd_contact_agent");
                dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("cd_call_agent"), "cd_call_agent");
                if(param.get("cd_contact_agent").equals("71801")){
                    dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("nm_contact_agent"), "nm_contact_agent");
                }
                if(param.get("cd_contact_agent").equals("71802")){
                    dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("ds_contact_agent"), "ds_contact_agent");
                }
            }
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("dt_agent"), "dt_agent");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("nm_agent"), "nm_agent");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("nm_agent_ceo"), "nm_agent_ceo");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("nm_agent_manager"), "nm_agent_manager");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(param.get("ds_agent_url"), "ds_agent_url");
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
        resultMap.put("no_cooperate", paramMap.get("no_cooperate"));
        resultMap.put("no_req", noReq);
        if(!cdResult.equals("F")){
            resultMap.put("ds_screen", "대기");
        }
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
