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


import net.orpiske.tcs.service.core.domain.ReferenceCreateData;
import net.orpiske.tcs.service.core.events.request.RequestCreateReference;
import net.orpiske.tcs.service.core.events.response.ReferenceCreateEvent;
import net.orpiske.tcs.service.core.service.TagCloudService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/references")
public class ReferencesCommandsController {
    private static final Logger logger =
            Logger.getLogger(ReferencesCommandsController.class);

    @Autowired
    private TagCloudService tagCloudService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateCloud(@RequestBody ReferenceCreateData data) {
        ReferenceCreateEvent tagCloudEvent = tagCloudService.createReference(
                new RequestCreateReference(data));

        if (!tagCloudEvent.isEntityFound()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (!tagCloudEvent.isUpdated()) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity(HttpStatus.OK);
    }

}
