INSERT INTO public.bank_account(id, money, "number")
VALUES (1, 0, '1112221111'),
       (7, 100000, '7777777');

INSERT INTO public.passport(passport_id, "number", series)
VALUES (1, '111222', '1111');

INSERT INTO public.usr(id, active, bank_commission, first_name, last_name, password, patronymic, username, bank_account,
                       passport)
VALUES (5, true, 10, 'Мельников', 'Арсений', 'a', 'Алексеевич', 'a', 1, 1);

INSERT INTO public.user_role(user_id, roles)
VALUES (5, 'MANAGER');

INSERT INTO public.credit_card(id, is_active, limit_of_credits, "number", pin_code, valid_until, client_id)
VALUES (1, true, 200000, 5400540011111111, 1111, '2031-12-12', 5);

INSERT INTO public.deposit_rate(id, interest_rate, is_adding, is_capitalized, is_early_closing, is_replenish,
                                is_renewable, max_term, min_term, name)

VALUES (1, 6, false, false, false, false, false, 24, 6, 'Охраняй'),
       (2, 4.5, false, true, false, true, true, 12, 1, 'Усложняй'),
       (3, 3, true, true, true, true, true, 12, 1, 'Исполняй');