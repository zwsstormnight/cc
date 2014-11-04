            <form action="/computer/${computer.id}" method="POST" encType="multipart/form-data">
                <fieldset>
                    <div class="clearfix">
                        <label for="name">${message("example.computer.name")}</label>
                        <div class="input">
                        [@spring.bind "computer.name" /]
                        <input type="text" id="name" name="name" value="${computer.name}" >[@spring.showErrors "<br/>"/]
                            <span class="help-inline">${message("example.field.required")}</span> 
                        </div>
                    </div>
                    <div class="clearfix ">
                        <label for="introduced">${message("example.computer.introduced")}</label>
                        <div class="input">
                        <input type="text" id="introduced" name="introduced" value="${computer.introduced}" >
                            <span class="help-inline">${message("example.date.sample")}</span> 
                        </div>
                    </div>
                    <div class="clearfix ">
                        <label for="discontinued">${message("example.computer.discontinued")}</label>
                        <div class="input">
                        <input type="text" id="discontinued" name="discontinued" value="${computer.discontinued}" >
                            <span class="help-inline">${message("example.date.sample")}</span> 
                        </div>
                    </div>

                    <div class="clearfix ">
                        <label for="company">${message("example.company.name")}</label>
                        <div class="input">
                            <select id="company" name="companyId" >
                                <option class="blank" value="">${message("example.choose.company")}</option>
                                [@company_list orderBy = "name asc"]
                                [#list companies as company]
                                    <option value="${company.id}">${company.name}</option>
                                [/#list]
                                [/@company_list]
                            </select>
                            <span class="help-inline"></span> 
                        </div>
                    </div>
                    
                    <div class="clearfix ">
                        <label for="image">${message("example.image.name")}</label>
                        <div class="input">
                            <input type="file" name="file" \/>
                        </div>
                    </div>
                </fieldset>
            
                <div class="actions">
                    <input type="submit" value="[#if (computer.id)??]${message("example.computer.save")}[#else]${message("example.computer.create")}[/#if]" class="btn primary">${message("example.or")}
                    <a href="/computer" class="btn">${message("example.cancel")}</a> 
                </div>
            </form>
            
            [#if computer??]
            <form action="/computer/${computer.id}/delete" method="POST" class="topRight">
                <input type="submit" value="${message("example.compter.delete")}" class="btn danger">
            </form>
            [/#if]