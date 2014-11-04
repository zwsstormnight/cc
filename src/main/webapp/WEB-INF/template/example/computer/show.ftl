[#import "example/computer/layout.ftl" as layout]
[@layout.main]
            <h1>${message("example.computer.edit")}</h1>
            <table class="computers zebra-striped">
                <tr>
                    <td>${message("example.computer.name")}</td>
                    <td>${computer.name}</td>
                </tr>
                <tr>
                    <td>${message("example.computer.introduced")}</td>
                    <td>${(computer.introduced?string('yyyy-MM-dd'))!}</td>
                </tr>
                <tr>
                    <td>${message("example.computer.discontinued")}</td>
                    <td>${(computer.discontinued?string('yyyy-MM-dd'))!}</td>
                </tr>
                <tr>
                    <td>${message("example.company.name")}</td>
                    <td>${(computer.company.name)!}</td>
                </tr>
                [#if computer.image?has_content]
                <tr>
                    <td>${message("example.image.name")}</td>
                    <td><img src="${(computer.image)!}"/></td>
                </tr>
                [/#if]
            </table>
        
            <div class="actions">
                <a class="btn success" id="new" href="/computer/${computer.id}">${message("example.computer.edit")}</a>
                <a href="/computer" class="btn">${message("example.cancel")}</a> 
            </div>
            
[/@layout.main]

