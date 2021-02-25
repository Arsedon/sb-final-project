<#import "../parts/common.ftlh" as c>
<#import "../parts/login.ftlh" as l>


<@c.page>
    <#if Currencies?hasContent>
        <form action="/trans" method="post">
            <p class="fw-bolder"><h5>Перевод на карту</h5></p>
            <div class="input-group">
                <input type="text" name="cardNumber" class="form-control" placeholder="Номер">
                <input type="text" name="moneyAmount" class="form-control" placeholder="Желаемая сумма">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-primary" type="submit">Выполнить перевод</button>
            </div>
        </form>
        <br>
        <form action="/creditreq" method="post">
            <p class="fw-bolder"><h5>Заявка на кредитование</h5></p>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Желаемая сумма:</label>
                <div class="col-sm-3">
                    <input type="text" name="sum" class="form-control" placeholder="Сумма кредита"/>
                </div>
            </div>
            <br>
            <#list Currencies as currency>
                <div class="input-group mb-3">
                    <div class="input-group-text">
                        <input class="form-check-input mt-0" type="checkbox" name="${currency}"
                               aria-label="Checkbox for following text input">
                    </div>
                    <input type="text" placeholder="${currency.toString()}" class="form-control"
                           aria-label="Text input with checkbox">
                </div>
            </#list>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div><input class="btn btn-primary" type="submit" value="Создать"></div>
        </form>
        <br>

        <p class="fw-bolder"><h5>Заявка на изменение условий кредитования</h5></p>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Межвалютная комиссия</th>
                <th scope="col">Процент пени по кредиту</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <form action="/personReq" method="post">
                    <div class="input-group">
                        <td><input type="text" name="bankCommission" class="form-control"
                                   placeholder="% комиссии при переводе валюты"></td>
                        <td><input type="text" name="penaltyValue" class="form-control"
                                   placeholder="% пени по кредитным неуплатам"></td>
                        <td>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-primary" type="submit">Отправить заявку</button>
                        </td>
                    </div>
                </form>
            </tr>
            </tbody>
        </table>
        <br>
        <br>

        <#if rates?hasContent>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Проценты по вкаладу</th>
                    <th scope="col">Срок</th>
                    <th scope="col">Снятие %</th>
                    <th scope="col">Пополнение</th>
                    <th scope="col">Досрочное прекращение</th>
                    <th scope="col">Капитализация</th>
                    <th scope="col">Автопродление</th>
                    <th scope="col">Название вклада</th>
                    <th scope="col"></th>

                </tr>
                </thead>
                <tbody>
                <#list rates as rate>
                    <tr>
                        <td>${rate.interestRate} %</td>
                        <td>от ${rate.minTerm} до ${rate.maxTerm}</td>
                        <td>${rate.Replenish()}</td>
                        <td>${rate.Adding()}</td>
                        <td>${rate.EarlyClosing()}</td>
                        <td>${rate.capitalized()}</td>
                        <td>${rate.Renewable()}</td>

                        <td>${rate.name}</td>

                        <td><a class="nav-link active" aria-current="page" href="/main/${rate.id}">Конфигурация
                                вклада</a>
                            </div>
                            </form>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>
    </#if>
    <#if message?hasContent>
        ${message}
    </#if>
</@c.page>