<#import "../parts/common.ftlh" as c>

<@c.page>
    <#if deposits?has_content>
        <h2 class="display-4">Мои вклады</h2>
        <br>
        <br>
        <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Процентный остаток</th>
                <th scope="col">Процентная стаавка</th>
                <th scope="col">Срок вклада</th>
                <th scope="col">Возможность снятия</th>
                <th scope="col">Возможность пополнения</th>
                <th scope="col">Закрытие вклада</th>
                <th scope="col">Капитализация</th>
                <th scope="col">Автопродление</th>
                <th scope="col">Начисление %</th>


            </tr>
            </thead>
            <tbody>
            <#list deposits as deposit>
                <#if deposit.isActive() = true>
                    <tr>
                        <td>${deposit.accruedInterest}</td>
                        <td>${deposit.interestRate}</td>
                        <td>${deposit.term} </td>
                        <td><#if deposit.isReplenish() = true>
                            <form action="/listOfDeposits/${deposit.id}/taking" method="post">
                                <div class="input-group">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <input type="text" name="moneyAmount" class="form-control" placeholder="Cумма">
                                    <button class="btn btn-primary" type="submit">снять</button>
                                    <#else>
                                    <div class="input-group">
                                        <button class="btn btn-danger" type="button">Без снятия</button>
                                        </#if>
                                    </div>
                            </form>
                        <td><#if deposit.isAdding() = true>
                            <form action="/listOfDeposits/${deposit.id}/put" method="post">
                                <div class="input-group">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <input type="text" name="moneyAmount" class="form-control" placeholder="Cумма">
                                    <button class="btn btn-primary" type="submit">Пополнить</button>
                                    <#else>
                                    <div class="input-group">
                                        <button class="btn btn-danger" type="button">Без пополнения</button>
                                        </#if>
                                    </div>
                            </form>
                        <td><#if deposit.isEarlyClosing() = true>
                            <form action="/listOfDeposits/${deposit.id}/delete" method="post">
                                <div class="input-group">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <button class="btn btn-primary" type="submit">Закрыть</button>
                                </div>
                            </form>
                            <#else>
                            <div class="input-group">
                                <button class="btn btn-danger" type="button">Недоступно</button>
                                </#if>
                        <td>${deposit.isCapitalized()?string("Да", "Нет")}</td>
                        <td>${deposit.isRenewable()?string("Да", "Нет")}</td>
                        <td>${deposit.getDateOfNextCalculationOfInterests()?string}</td>

                    </tr>
                </#if>
            </#list>
            </tbody>
        </table>
    </#if>
    <#if message?has_content>
        ${message}
    </#if>
</@c.page>