<#import "../parts/common.ftlh" as c>

<@c.page>
    <h2 class="display-4">История переводов</h2>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Клиент</th>
            <th scope="col">Сумма перевода</th>
            <th scope="col">Номер карты</th>
            <th scope="col"></th>
            <th scope="col"></th>

        </tr>
        </thead>
        <tbody>
        <#list transfers as request>
        <tr>
            <td>${request.client.firstName} ${request.client.lastName} ${request.client.patronymic} </td>
            <td>${request.moneyAmount}</td>
            <td>${request.cardNumber}</td>
            </#list>
        </tbody>
    </table>
</@c.page>