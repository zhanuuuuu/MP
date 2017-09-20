package com.wx.v1;

import tool.HttpTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by xwy_brh on 2017/9/14.
 */
@WebServlet(name = "Servlet")
public class CreateVipCard extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String access_token = "5JMEnomqZdYyb7b_j-MBuNtVmg1duwoG3fnJ9Z7iWZ5MpwF9g4Qni_YXmgsdTP4UF7H9jpdptONkNfoZJqYFqsUFc_VQeNZyDraEyTC6VbD6dwe2Bng9HEbn5-bX0YXHJIKeACARQS";

        String CREATE_VIP_CARD_DOMAIN_URL = "https://api.weixin.qq.com/card/create?access_token=" + access_token;
        HttpTool.POST(CREATE_VIP_CARD_DOMAIN_URL, "");


    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
