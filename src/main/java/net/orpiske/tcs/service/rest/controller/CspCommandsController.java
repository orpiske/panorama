/**
 Copyright 2014 Otavio Rodolfo Piske

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package net.orpiske.tcs.service.rest.controller;

import net.orpiske.tcs.service.core.domain.Csp;
import net.orpiske.tcs.service.core.events.request.RequestCreateCspEvent;
import net.orpiske.tcs.service.core.events.response.CspCreateEvent;
import net.orpiske.tcs.service.core.service.TagCloudService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@Controller
@RequestMapping("/csp")
public class CspCommandsController {
    private static final Logger logger = Logger.getLogger(CspCommandsController.class);

    @Autowired
    private TagCloudService tagCloudService;

    @RequestMapping(value = "/{csp}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Csp> createCspTagCloud(@RequestBody final Csp csp, UriComponentsBuilder builder) {

        if (logger.isDebugEnabled()) {
            logger.debug("CSP command controller handling a create CSP request for " + csp);
        }

        CspCreateEvent tagCloudEvent = tagCloudService.createCsp(
                new RequestCreateCspEvent(csp));

        Csp cspObj = tagCloudEvent.getCsp();

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setLocation(builder.path("/csp/{csp}")
                .buildAndExpand(cspObj.getName()).toUri());

        return new ResponseEntity<Csp>(cspObj, httpHeaders, HttpStatus.OK);
    }
}
