<#include "security.ftlh">
<#import "login.ftlh" as l>


<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Simple Bank</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Домой</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/main">Главная страница</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/reqlist">Мои заявки</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/transfersHistory">Мои переводы</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/listOfCredits">Мои кредиты</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/listOfDeposits">Мои Вклады</a>
                </li>
            </ul>
            <div class="navbar-text me-3 ">${name}</div>
            <@l.logout />
        </div>
</nav>
<#if isManager>
    <nav class="navbar navbar-light" style="background-color: #e3f2fd;">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Панель Менеджера</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/request">Кредиты</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/trReq">Переводы</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/personConditionsList">Персональные
                            предложения</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/depositRateEdit">Редактирование тарифных
                            планов</a>
                    </li>
                </ul>
    </nav>
</#if>