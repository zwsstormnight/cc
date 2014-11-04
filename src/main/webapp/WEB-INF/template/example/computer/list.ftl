[#macro sort_q sort][#if (sort)??][#list sort.iterator() as order][#if order_index != 0]&[/#if]sort=${order.property},${order.direction}[/#list][/#if][/#macro]

[#macro header title orderProperty sort]
                            <th class="col header [#if (sort)??][#list sort.iterator() as order][#if order.roperty == orderProperty][#if order.direction == "ASC"]headerSortDown[#else]headerSortUp[/#if][/#if][#break/][/#list][/#if]">
                                <a href="/computer?sort=${orderProperty},[#if (sort)??][#list sort.iterator() as order][#if order.direction == "DESC"]ASC[#else]DESC[/#if][#break/][/#list][/#if]">${title}</a>
                            </th>
                            
[/#macro]



[#import "example/computer/layout.ftl" as layout]
[@layout.main]
            <h1>[@spring.messageArgs "example.computer.header", [page.totalElements]/]</h1>
            <div id="actions">
                <form action="/computer" method="GET" >
                    <input type="search" id="searchbox" name="f" value="${filter}" placeholder="[@spring.message "example.filter.hint" /]">
                    <input type="submit" id="searchsubmit" value="${message("example.filter_by_name")}" class="btn primary">
                </form>
                <a class="btn success" id="add" href="/computer/new">${message("example.computer.new")}</a>
            </div>
            <table class="computers zebra-striped">
                <thead>
                    <tr>
                        [@header "Computer name" "name" pageable.sort/]
                        [@header "Introduced" "introduced" pageable.sort/]
                        [@header "Discontinued" "discontinued" pageable.sort/]
                        [@header "Company" "company" page.sort/]
                    </tr>
                </thead>
                <tbody>
                [#if page.content?has_content]
                    [#list page.content as computer]
                    <tr>
                        <td><a href="${computer.path}" /]">${computer.name}</a></td>
                        <td>${computer.introduced}</td>
                        <td>${computer.discontinued}</td>
                        <td>${(computer.company.name)!}</td>
                    </tr>
                    [/#list]
                [/#if]
                </tbody>
            </table>
            <div id="pagination" class="pagination">
                <ul>
                [#if page.hasPrevious()]
                    <li class="prev">
                        <a href="/computer?page=${page.number - 1}&f=${filter}&[@sort_q pageable.sort /]">${message("example.page.previous")}</a>
                    </li> 
                [#else]
                    <li class="prev disabled">
                        <a>${message("example.page.previous")}</a>
                    </li>
                [/#if]
                <li class="current">
                    <a>[@spring.messageArgs "example.page.display", [pageable.offset + 1, pageable.offset + page.content.size(), page.totalElements]/]</a>
                </li>
                 [#if page.hasNext()]
                    <li class="prev">
                        <a href="/computer?page=${page.number + 1}&f=${filter}&[@sort_q pageable.sort /]">${message("example.page.next")}</a>
                    </li> 
                [#else]
                    <li class="next disabled">
                        <a>${message("example.page.next")}</a>
                    </li>
                [/#if]
                </ul>
            </div>
[/@layout.main]