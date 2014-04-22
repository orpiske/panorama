package panorama.gui

import net.orpiske.tcs.service.core.domain.*;

class TagListTagLib {


    def tagList = { attrs , body ->
        List<Tag> tagList = attrs.list;

        for (Tag tag : tagList) {
            out << body("domain" : tag.getDomain(),
                    "word" : tag.getWord(),
                    "occurrences" : String.valueOf(tag.getOccurrences()))
        }
    }
}
