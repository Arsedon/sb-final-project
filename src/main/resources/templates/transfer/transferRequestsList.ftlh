<#import "../parts/common.ftlh" as c>

<@c.page>
    <h5>Переводы на согласование</h5>

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
        <#list transferRequest as request>
        <tr>
            <td>${request.client.firstName} ${request.client.lastName} ${request.client.patronymic} </td>
            <td>${request.moneyAmount}</td>
            <td>${request.cardNumber}</td>
            <td>
                <form action="/trReq/${request.id}/add" method="post">
                    <button class="btn btn-primary" type="submit">Добавить</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </td>
            <td>
                <form action="/trReq/${request.getId()}/delete" method="post">
                    <button class="btn btn-danger" type="submit">Отклонить</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </td>
            </#list>
        </tbody>
    </table>
</@c.page>