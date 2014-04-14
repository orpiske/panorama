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

import net.orpiske.tcs.service.core.domain.Domain;
import net.orpiske.tcs.service.core.domain.TagCloud;
import net.orpiske.tcs.service.core.events.request.RequestTagCloudEvent;
import net.orpiske.tcs.service.core.events.response.TagCloudEvent;
import net.orpiske.tcs.service.core.service.TagCloudService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tagcloud")
public class TagCloudQueriesController {
    private static final Logger logger =
            Logger.getLogger(TagCloudQueriesController.class);

    @Autowired
    private TagCloudService tagCloudService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TagCloud requestTagCloud() {
        if (logger.isDebugEnabled()) {
            logger.debug("TagCloud command controller handling a tag cloud request");
        }

        TagCloudEvent tagCloudEvent = tagCloudService.requestTagCloud(new RequestTagCloudEvent());

        return tagCloudEvent.getCspTagCloud();
    }


    @RequestMapping(value = "/{domain}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TagCloud> requestCspTagCloud(@RequestBody final Domain domain) {

        if (logger.isDebugEnabled()) {
            logger.debug("Requesting the CSP tag cloud for " + domain);
        }

        TagCloudEvent tagCloudEvent = tagCloudService.requestTagCloud(
                new RequestTagCloudEvent(domain));

        if (!tagCloudEvent.isEntityFound()) {
            return new ResponseEntity<TagCloud>(HttpStatus.NOT_FOUND);
        }

        TagCloud tagCloud = tagCloudEvent.getCspTagCloud();

        return new ResponseEntity<TagCloud>(tagCloud, HttpStatus.OK);
    }




}
