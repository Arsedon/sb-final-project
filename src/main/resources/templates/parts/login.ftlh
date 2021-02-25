<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Логин:</label>
            <div class="col-sm-3">
                <input type="text" name="username" class="form-control" placeholder="Логин"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль:</label>
            <div class="col-sm-3">
                <input type="password" name="password" class="form-control" placeholder="Пароль"/>
            </div>
        </div>
        <#nested>
        <div>
            <#if !isRegisterForm>
                <a href="/registration">Регистрация</a>
            </#if>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit"><#if isRegisterForm>Создать<#else>Войти</#if></button>
        </div>
    </form>
</#macro>


<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Выйти из системы</button>
    </form>
</#macro>


