<#import "../parts/common.ftlh" as c>

<@c.page>
    <h5>Создание новой кредитной карты</h5>
    <br>

    <form action="/request/${request}/cardcreate" method="post">
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="number" id="floatingInput"
                   placeholder="Добавочный номер карты">
            <label for="floatingInput">Добавочный номер карты</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="pinCode" id="floatingPassword"
                   placeholder="Пин - код">
            <label for="floatingPassword">Пин - код</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="limitOfCredits" id="floatingPassword"
                   placeholder="лимит">
            <label for="floatingPassword">Общий лимит по кредитам</label>
        </div>
        <button class="btn btn-primary" type="submit">Создать</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
    <br>
    ${message?ifExists}
    <br>
</@c.page>