<#import "../parts/common.ftlh" as c>

<@c.page>
    <h5>Клиентские заявки на кредит</h5>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Клиент</th>
            <th scope="col">Запрошенная сумма</th>
            <th scope="col">Доля пени</th>
            <th scope="col">Комиссия</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list request as request>
            <tr>
                <td>${request.client.firstName} ${request.client.lastName} ${request.client.patronymic} </td>
                <td>${request.requestedSum} <#list request.currency as currency>
                        <#if currency?has_content>
                            ${currency}
                        </#if>
                    </#list> </td>
                <td>${request.client.getBankCommission()} %</td>
                <td>${request.penaltyValue} %</td>

                <td><a href="/request/${request.id}">Задать условия</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>