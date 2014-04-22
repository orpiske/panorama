package net.orpiske.panorama.controller

import net.orpiske.tcs.service.core.domain.*;
import net.orpiske.tcs.client.base.*;
import net.orpiske.tcs.client.services.*;

class AnalyticsController {

    def index() {
        DefaultEndPoint defaultEndPoint = new DefaultEndPoint("http://localhost:8080");
        TagCloudServiceClient client = new TagCloudServiceClient(defaultEndPoint);


        TagCloud tc = client.requestTagCloud();

        [ tagList : tc.getTagCloud() ];
    }

    def list() {
        DefaultEndPoint defaultEndPoint = new DefaultEndPoint("http://localhost:8080");
        TagCloudServiceClient client = new TagCloudServiceClient(defaultEndPoint);


        TagCloud tc = client.requestTagCloud();

        [ tagList : tc.getTagCloud() ];
    }
}
