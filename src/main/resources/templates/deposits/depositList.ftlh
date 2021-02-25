<#import "../parts/common.ftlh" as c>

<@c.page>
    <#if depositRate?has_content>
        <h2 class="display-4">Конфигуратор вклада</h2>
        <br>
        <br>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Название вклада</th>
                <th scope="col">Процентная ставка</th>
                <th scope="col">Диапазон</th>
                <th scope="col">Возможность снятия</th>
                <th scope="col">Пополнение</th>
                <th scope="col">Досрочного закрытие</th>
                <th scope="col">Автопродление</th>

                <th scope="col">Позиции</th>

            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${depositRate.name}</td>
                <td>${depositRate.interestRate}</td>
                <td>от ${depositRate.minTerm} до ${depositRate.maxTerm} месяцев</td>
                <td>${depositRate.Replenish()}</td>
                <td>${depositRate.Adding()}</td>
                <td>${depositRate.EarlyClosing()}</td>
                <td>${depositRate.Renewable()}</td>

                <td>
                    <form action="/main/${depositRate.id}" method="post">
                        <div class="input-group">
                            <input type="text" name="moneyAmount" class="form-control" placeholder="Сумма">
                            <input type="text" name="term" class="form-control" placeholder="Срок">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="btn btn-primary" type="submit">Задать</button>
                        </div>
                    </form>
            </tr>
            </tbody>
        </table>
    <#else>
        ${message}
    </#if>
</@c.page>