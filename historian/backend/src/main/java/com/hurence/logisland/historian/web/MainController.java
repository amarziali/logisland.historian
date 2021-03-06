/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hurence.logisland.historian.web;

import com.hurence.logisland.historian.service.TagsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
class MainController {

    @Autowired
    TagsApiService tagsApiService;

    @GetMapping(path = "/mytags")
    public String getTags(Principal principal, Model model){

        long start = System.currentTimeMillis();
        model.addAttribute("tags", tagsApiService.getAllTags("*"));
        long requestTime  = System.currentTimeMillis() - start;

     //   model.addAttribute("principal",principal);
        model.addAttribute("requestTime",requestTime);


        return "tags";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }

    @GetMapping(path = "/")
    public String index(){
        return "index";
    }
}