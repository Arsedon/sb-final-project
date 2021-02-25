<#import "../parts/common.ftlh" as c>
<#import "../parts/login.ftlh" as l>


<@c.page>
    <h5>Авторизация</h5>
    <@l.login "/login" false>
    </@l.login>
</@c.page>