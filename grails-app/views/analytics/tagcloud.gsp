<!DOCTYPE html>
<html>

<g:set var="siteRoot" value=".." />

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
                            <li><a href="${siteRoot}/">Home</a></li>
                            <li><a href="${siteRoot}/about">Sobre</a></li>
                            <li><a href="${siteRoot}/domains">Estatísticas</a></li>
                            <li><a href="${siteRoot}/contact">Contato</a></li>
                        </ul>
                    </div>
                </header>
            </div>

             <div id="left_menu">
                  <ul>
                     <li><a href="tagcloud">Tag Cloud</a></li>
                     <li><a href="occurrences">Ocorrências</a></li>
                     <li><a href="charts">Gráficos</a></li>
                     <li><a href="list">Lista de palavras</a></li>
                 </ul>
             </div>

            <div id="content">
                <p>
                   <g:tagCloud domains="${tagCloud}">

                   </g:tagCloud>


                </p>
            </div>
        </div>
    </body>
</html>
