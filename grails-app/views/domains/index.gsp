<!DOCTYPE html>
<html>

<head>
      <title>Panorama</title>
        <base href="."></base>

        <meta name="description" content="Panorama da Internet brasileira" />
        <meta name="keywords" content="internet brasileira, provedores, analytics" />
        <meta http-equiv="content-type" content="text/html; charset=UTF-8" />

        <r:external uri="/css/style.css" />
        <r:external uri="/js/chart/nv.d3.js" />

    </head>

    <body>
        <div id="container">
            <div id="header">
                <header>
                    <div id="top_menu">
                        <ul>
                            <li><a href="./">Home</a></li>
                            <li><a href="about">Sobre</a></li>
                            <li><a href="contact">Contato</a></li>
                        </ul>
                    </div>
                </header>
            </div>

             <div id="left_menu">
                   <ul>
                        <g:tagDomains list="${domainList}" />
                  </ul>
              </div>

<!--
            <div id="content">
                <p>
                   <g:tagList list="${tagList}" size="${tagList}.size">
                        Há ${occurrences} ocorrências de <code>${word}</code> para o domínio ${domain}<br>
                   </g:tagList>
                </p>
            </div>
-->
        </div>
    </body>
</html>
