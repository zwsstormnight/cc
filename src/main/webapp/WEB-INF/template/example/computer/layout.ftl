[#macro main]
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <title>${message("example.title")}</title>
        <meta charset="utf-8"/> 
        <link rel="stylesheet" type="text/css" media="screen" href="/assets/stylesheets/bootstrap.min.css"> 
        <link rel="stylesheet" type="text/css" media="screen" href="/assets/stylesheets/main.css"> 
    </head>
    
    <body>
        <header class="topbar">
            <h1 class="fill">
                <a href="/computer">
                    ${message("example.header")}
                </a>
            </h1>
        </header>
        <section id="main">
            [#nested/]
        </section>
    </body>
</html>

[/#macro]
