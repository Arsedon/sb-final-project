<#import "../parts/common.ftlh" as c>

<@c.page>
    <h2 class="display-4">Мои заявки на кредит</h2>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Клиент</th>
            <th scope="col">Запрошенная сумма</th>
            <th scope="col">Процентная ставка</th>
            <th scope="col">Окончательная сумма</th>
            <th scope="col">Общий кредитный лимит</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list request as request>
            <tr>
                <td>${request.client.firstName} ${request.client.lastName} ${request.client.patronymic} </td>
                <td>${request.requestedSum}</td>
                <td>${request.interestRate}</td>
                <td>${request.finalSum}</td>
                <td>${request.cardOfClient.limitOfCredits}</td>
                <td>
                    <form action="/reqlist/${request.getId()}/delete" method="post">
                        <button class="btn btn-danger" type="submit">Отклонить</button>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                </td>
                <td>
                    <form action="/reqlist/${request.getId()}/add" method="post">
                        <button class="btn btn-primary" type="submit">Принять</button>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                </td>
                </td>
            </tr>
        <#else>
            <p>На данный момент отсутствуют активные заявки</p>
        </#list>
        </tbody>
    </table>
</@c.page>