<#import "../parts/common.ftlh" as c>

<@c.page>
    <h2 class="display-4">Мои кредиты</h2>
    <#if user?has_content>

    </#if>
    <#if message?has_content>
        ${message}
    </#if>
    <br>
    <br>
    <#if credits?has_content>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Остаточный платеж</th>
                <th scope="col">Процент пени</th>
                <th scope="col">Дата взятия кредита</th>
                <th scope="col">Крайняя дата оплаты кредита</th>
                <th scope="col">Кредитная карта</th>
                <th scope="col"></th>

            </tr>
            </thead>
            <tbody>
            <#list credits as credit>
                <tr>
                    <td>${credit.finalSum}</td>
                    <td>${credit.penaltyValue}</td>
                    <td>${credit.dateOfTakingCredit}</td>
                    <td>${credit.maturity}</td>
                    <td>${credit.creditCard.number}</td>
                    <td>
                        <form action="/listOfCredits" method="post">
                            <div><label> Платеж: <input type="text" name="payment"/> </label>
                                <input type="hidden" value="${_csrf.token}" name="_csrf">
                                <input type="hidden" value="${credit.id}" name="id">
                                <button class="btn btn-primary" type="submit">внести</button>
                            </div>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h5 class="display-6">Поздравляем! На данный момент кредиты отсутствуют</h5>
    </#if>
    <br>
    <br>
    <br>
    <h2 class="display-4">Кредитная карта</h2>
    <#if delete?has_content>
        ${delete}
    </#if>
    <#if cards?has_content>
        <#if cards.isActive() = true>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">ФИО</th>
                    <th scope="col">Номер карты</th>
                    <th scope="col">Действительна до</th>
                    <th scope="col">Кредитный лимит</th>
                    <th scope="col"></th>

                </tr>
                </thead>
                <tbody>

                <tr>
                    <td>${user.firstName} ${user.lastName} ${user.patronymic}</td>
                    <td>${cards.number}</td>
                    <td>${cards.validUntil}</td>
                    <td>${cards.limitOfCredits}</td>
                    <td>
                        <form action="/listOfCredits/${cards.id}/delete" method="post">
                            <input type="hidden" value="${_csrf.token}" name="_csrf">
                            <input type="hidden" value="${cards.id}" name="id">
                            <button class="btn btn-danger" type="submit">Закрыть карту</button>
                            </div>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        <#else>
            <br>
            <br>
            <h5 class="display-6">Кредитных карт нет!</h5>
        </#if>
    </#if>

</@c.page>