package panorama.gui

import net.orpiske.tcs.service.core.domain.*;
import net.orpiske.tcs.client.base.*;
import net.orpiske.tcs.client.services.*;

class TagcloudController {

    def index() {
        DefaultEndPoint defaultEndPoint = new DefaultEndPoint("http://localhost:8080");
        TagCloudServiceClient client = new TagCloudServiceClient(defaultEndPoint);


        TagCloud tc = client.requestTagCloud();

        List<Tag> tagList = tc.getTagCloud();

        for (Tag tag : tagList) {
            render "<br> Domain: " + tag.getDomain() + " Word: " + tag.getWord() + " Occurrences: " + String.valueOf(tag.getOccurrences())
        }

        render tc.toString();
	}
}
