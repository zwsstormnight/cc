[#import "example/computer/layout.ftl" as layout]
[@layout.main]
            <h1>${message("example.computer.add")}</h1>
            [#include "example/computer/_form.ftl"]
[/@layout.main]

