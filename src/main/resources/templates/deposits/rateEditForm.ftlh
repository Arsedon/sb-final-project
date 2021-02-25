<#import "../parts/common.ftlh" as c>

<@c.page>
    <#if rates?has_content>
        <h2 class="display-4">Тарифные планы</h2>
        <br>
        <br>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Название вклада</th>
                <th scope="col">Процентная стаавка</th>
                <th scope="col">Срок вклада от</th>
                <th scope="col">Срок вклада до</th>
                <th scope="col">Снятие</th>
                <th scope="col">Пополнение</th>
                <th scope="col">Досрочное закрытие</th>
                <th scope="col">Капитализация</th>
                <th scope="col">Автопродление</th>
                <th scope="col"></th>


            </tr>
            </thead>
            <tbody>
            <#list rates as rate>
                <tr>
                    <form action="/depositRateEdit/${rate.id}" method="post">
                        <div class="input-group">
                            <td><input type="text" name="name" class="form-control" placeholder="${rate.name}"></td>
                            <td><input type="text" name="interestRate" class="form-control"
                                       placeholder="${rate.interestRate}"></td>
                            <td><input type="text" name="minTerm" class="form-control" placeholder="${rate.minTerm}">
                            </td>
                            <td><input type="text" name="maxTerm" class="form-control" placeholder="${rate.maxTerm}">
                            </td>

                            <td>
                                <div class="form-check form-switch">
                                    <input <#if rate.isReplenish() = true >
                                            checked
                                    </#if>
                                    <input name="replenish" class="form-check-input" type="checkbox"
                                           id="flexSwitchCheckChecked">
                                    <label class="form-check-label" for="flexSwitchCheckChecked"></label>
                                </div>
                            </td>

                            <td>
                                <div class="form-check form-switch">
                                    <input <#if rate.isAdding() = true >
                                        checked
                                    </#if>
                                            name="adding" class="form-check-input" type="checkbox"
                                            id="flexSwitchCheckChecked">
                                    <label class="form-check-label" for="flexSwitchCheckChecked"></label>
                                </div>
                            </td>

                            <td>
                                <div class="form-check form-switch">
                                    <input <#if rate.isEarlyClosing() = true >
                                        checked
                                    </#if>
                                            name="earlyClosing" class="form-check-input" type="checkbox"
                                            id="flexSwitchCheckChecked">
                                    <label class="form-check-label" for="flexSwitchCheckChecked"></label>
                                </div>
                            </td>

                            <td>
                                <div class="form-check form-switch">
                                    <input <#if rate.isCapitalized() = true >
                                        checked
                                    </#if>
                                            name="capitalize" class="form-check-input" type="checkbox"
                                            id="flexSwitchCheckChecked">
                                    <label class="form-check-label" for="flexSwitchCheckChecked"></label>
                                </div>
                            </td>

                            <td>
                                <div class="form-check form-switch">
                                    <input <#if rate.isEarlyClosing() = true >
                                        checked
                                    </#if>
                                            name="renewable" class="form-check-input" type="checkbox"
                                            id="flexSwitchCheckChecked">
                                    <label class="form-check-label" for="flexSwitchCheckChecked"></label>
                                </div>
                            </td>

                            <td><input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <button class="btn btn-primary" type="submit">Задать</button>
                        </div>
                    </form>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        ${message}
    </#if>
</@c.page>