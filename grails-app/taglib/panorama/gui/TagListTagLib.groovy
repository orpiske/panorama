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

    def tagDomains = { attrs , body ->
        List<Domain> domainList = attrs.list;


        for (Domain domain : domainList) {
            out << "<li><a href=\"analytics?domain=" << domain.getDomain() << \
                "&name=" << domain.getName() << "\">" << domain.getName() << "</a></li>"
        }
    }


    def tagMinCloud = { attrs , body ->
        List<Tag> tagList = attrs.list;

        for (int i = 0; i < tagList.size(); i++) {
            Tag tag = tagList.get(i);

            int occurrences = tag.getOccurrences();
            int size = 0;


            if (occurrences < 5) {
                size = 12;
            }
            else {
                if (occurrences < 10) {
                    size = 16;
                }
                else {
                    if (occurrences < 30) {
                        size = 18;
                    }
                    else {
                        size = 22;
                    }
                }
            }

            out << "<span style=\"font-size:" << String.valueOf(tag.getOccurrences()) << \
              "px;\">" << tag.getWord() << " </span>";
        }
    }



    def tagCloud = { attrs, body ->
        Map<Domain, List<Tag>> tagCloud = attrs.domains;


        // Set<String> domainSet = new HashSet<>();

        /*
        for (Tag tag : tagList) {
            String domain = tag.getDomain();

            domainSet.add(domain);
            if (!domainSet.contains(domain)) {

            }
        }
        */

        Iterator<Map.Entry<Domain, List<Tag>>> it = tagCloud.entrySet().iterator();

        while (it.hasNext()) {
            int i = 0;
            Map.Entry<Domain, List<Tag>> entry = it.next();
            Domain domain = entry.getKey();

            out << "<div id=\"domainContent\">"
            out << "<p>" << domain.getDomain() << "</p>"

            for (Tag tag : entry.getValue()) {
                i++;

                int occurrences = tag.getOccurrences();
                int size = 0;


                if (occurrences < 5) {
                    size = 12;
                }
                else {
                    if (occurrences < 10) {
                        size = 16;
                    }
                    else {
                        if (occurrences < 30) {
                            size = 18;
                        }
                        else {
                            size = 22;
                        }
                    }
                }

                out << "<span style=\"font-size:" << String.valueOf(tag.getOccurrences()) << "px;\">" << tag.getWord() << " </span>";
            }

            out << "</div>"


        }
    }
}
