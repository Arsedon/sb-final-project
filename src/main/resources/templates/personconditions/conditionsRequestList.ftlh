<#import "../parts/common.ftlh" as c>

<@c.page>
    <h5>Клиентские заявки на кредит</h5>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Клиент</th>
            <th scope="col">Остаток по кредиту</th>
            <th scope="col">Доля пени</th>
            <th scope="col">Комиссия</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list personRequests as request>
            <#if request?has_content>
                <tr>
                <td>${request.client.firstName} ${request.client.lastName} ${request.client.patronymic} </td>
                <td><#list request.getClient().getCreditCard().getCredits()?if_exists as credit>
                        <#if credit?has_content>
                            ${credit.finalSum}
                        </#if>
                    </#list> </td>
                <td>${request.bankCommission} %</td>
                <td>${request.penaltyValue} %</td>
                <td>
                    <form action="/personConditionsList/${request.getId()}/delete" method="post">
                        <button class="btn btn-danger" type="submit">Отклонить</button>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                </td>
                <td>
                    <form action="/personConditionsList/${request.getId()}/add" method="post">
                        <button class="btn btn-primary" type="submit">Принять</button>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                </td>
                </td>
            </#if>

        <#--                <td><a href="/request/${request.id}">Задать условия</a></td>-->
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>