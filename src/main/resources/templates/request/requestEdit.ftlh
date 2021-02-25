<#import "../parts/common.ftlh" as c>

<@c.page>
    <#if request?has_content>
        <h5>Создание предложения для клиента</h5>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Клиент</th>
                <th scope="col">Запрошенная сумма</th>
                <th scope="col">Номер кредитной карты</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${request.client.firstName} ${request.client.lastName} ${request.client.patronymic} </td>
                <td>${request.requestedSum}</td>
                <td><#if request.cardOfClient?has_content && request.cardOfClient.isActive() = true>
                        ${request.cardOfClient.number}
                    <#else>
                        <a href="/request/${request.id}/cardcreate">Создать кредитную карту</a>
                    </#if>
                </td>
                <td>
                    <#list request.currency as currency>
                        <#if currency?has_content>
                            ${currency}
                        </#if>
                    </#list>
                </td>
                <td>
                    <form action="/request" method="post">
                        <div><label> Процентная ставка: <input type="text" name="interestRate"/> </label>
                            <button class="btn btn-primary" type="submit">Задать</button>
                            <input type="hidden" value="${request.id}" name="id">
                            <input type="hidden" value="${_csrf.token}" name="_csrf">
                        </div>
                    </form>
                </td>
            </tbody>
        </table>
    </#if>
    <#if message?has_content>
        ${message}
    </#if>
</@c.page>