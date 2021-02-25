<#import "../parts/common.ftlh" as c>
<#import "../parts/login.ftlh" as l>


<@c.page>
    <div class="mb-1">
        <h5>Регистрация нового пользователя</h5>
        <br>
    </div>
    ${message?ifExists}
    <@l.login "/registration" true>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Фамилия:</label>
            <div class="col-sm-3">
                <input type="text" name="firstName" class="form-control" placeholder="Фамилия"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Имя:</label>
            <div class="col-sm-3">
                <input type="text" name="lastName" class="form-control" placeholder="Имя"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Отчество:</label>
            <div class="col-sm-3">
                <input type="text" name="patronymic" class="form-control" placeholder="Отчество"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Номер паспорта:</label>
            <div class="col-sm-3">
                <input type="text" name="number" class="form-control" placeholder="Номер паспорта"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Серия паспорта:</label>
            <div class="col-sm-3">
                <input type="text" name="series" class="form-control" placeholder="Серия паспорта"/>
            </div>
        </div>
    </@l.login>
</@c.page>