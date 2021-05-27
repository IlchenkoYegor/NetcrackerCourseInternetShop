WHENEVER SQLERROR CONTINUE
-- create sequences here, ignoring errors

create sequence order_sequence
    start with 1
    increment by 1;
WHENEVER SQLERROR EXIT SQL.SQLCODE
WHENEVER SQLERROR CONTINUE
create sequence product_sequence
    start with 1
    increment by 1;
WHENEVER SQLERROR EXIT SQL.SQLCODE
WHENEVER SQLERROR CONTINUE
create sequence acc_sequence
    start with 1
    increment by 1;
WHENEVER SQLERROR EXIT SQL.SQLCODE
WHENEVER SQLERROR CONTINUE
create sequence order_detail_sq
    start with 1
    increment by 1;
WHENEVER SQLERROR EXIT SQL.SQLCODE

begin
    execute immediate 'create table ACCOUNTS
                       (
                           ACCOUNT_ID   integer primary key,
                           USER_NAME VARCHAR2(20 CHAR) not null unique ,
                           ACTIVE    NUMBER(1) not null,
                           PASSWORD  VARCHAR2(20 CHAR) not null,
                           USER_ROLE VARCHAR2(20) not null,
                           CUSTOMER_ADDRESS VARCHAR2(255 CHAR) not null,
                           CUSTOMER_EMAIL   VARCHAR2(128 CHAR) not null,
                           CUSTOMER_NAME    VARCHAR2(255 CHAR) not null,
                           CUSTOMER_PHONE   VARCHAR2(128 CHAR) not null

                       )';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            NULL; -- suppresses ORA-00955 exception
        ELSE
            RAISE;
        END IF;
END;
/

create or replace trigger acc_trigger
    before insert on ACCOUNTS for each row
begin
    select acc_sequence.nextval
    into :new.ACCOUNT_ID
    from dual;
end;
/
begin

    execute immediate 'create table PRODUCTS
                       (
                           Product_id  integer primary key,
                           Recomended  integer references PRODUCTS(PRODUCT_ID) on delete set null,
                           CODE        VARCHAR2(20 CHAR) not null unique,
                           IMAGE       BLOB,
                           NAME        VARCHAR2(255 CHAR) not null,
                           PRICE       FLOAT not null,
                           CREATE_DATE DATE default sysdate not null
                       ) ';

EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            NULL; -- suppresses ORA-00955 exception
        ELSE
            RAISE;
        END IF;
END;
/

create or replace  trigger product_trigger
    before insert on PRODUCTS for each row
begin
    select product_sequence.nextval
    into :new.Product_id
    from dual;
    select sysdate
    into :new.CREATE_DATE
    from dual;
end;
/

begin

    execute immediate 'create table PRODUCT_LOCATION
                       (
                           PRODUCT_ID  integer references products (Product_id) on delete cascade,
                           CITY        VARCHAR2(50 CHAR) not null,
                           COUNTRY     VARCHAR2(50 CHAR) not null,
                           ADDRES      VARCHAR2(50 CHAR) not null
                       ) ';

EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            NULL; -- suppresses ORA-00955 exception
        ELSE
            RAISE;
        END IF;
END;
/

begin

    execute immediate 'create table ORDERS
                       (

                           ORDER_ID         INTEGER not null constraint order_id primary key,
                           ACCOUNT_ID       Integer not null references ACCOUNTS(ACCOUNT_ID) on delete cascade ,
                           AMOUNT           FLOAT not null,
                           ORDER_DATE       TIMESTAMP(6) not null,
                           ORDER_NUM        NUMBER(10) not null constraint ORDER_UK unique

                       )';

EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            NULL; -- suppresses ORA-00955 exception
        ELSE
            RAISE;
        END IF;
END;
/

create or replace trigger order_trigger
    before insert on ORDERS for each row
begin
    select order_sequence.nextval
    into :new.ORDER_ID
    from dual;
    select sysdate
    into :new.ORDER_DATE
    from dual;
end;
/

begin
    execute immediate 'create table ORDER_DETAILS
                       (
                           DETAIL_ID  integer not null primary key,
                           AMOUNT     FLOAT not null,
                           PRICE      FLOAT not null,
                           QUANITY    NUMBER(10) not null,
                           ORDER_ID   integer not null references ORDERS (ORDER_ID),
                           PRODUCT_ID integer not null references PRODUCTS (PRODUCT_ID) on delete cascade
                       )';

EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            NULL; -- suppresses ORA-00955 exception
        ELSE
            RAISE;
        END IF;
END;
/
create or replace trigger order_detail_trigger
    before insert on ORDER_DETAILS for each row
begin
    select order_detail_sq.nextval
    into :new.DETAIL_ID
    from dual;
end;
/


delete from Accounts where USER_NAME = 'employee1' and PASSWORD = '123';
insert into Accounts (USER_NAME, ACTIVE, PASSWORD, USER_ROLE, CUSTOMER_ADDRESS, CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PHONE)
values ('employee1', 1, '123', 'EMPLOYEE', 'garber 2000', 'grisha@gmail.com', 'grisha', '+380100010009');

delete from Accounts where USER_NAME = 'manager1' and PASSWORD = '123';
insert into Accounts (USER_NAME, ACTIVE, PASSWORD, USER_ROLE, CUSTOMER_ADDRESS, CUSTOMER_EMAIL, CUSTOMER_NAME, CUSTOMER_PHONE)
values ('manager1', 1, '123', 'MANAGER', 'garber 2020', 'grisha1@gmail.com', 'grisha', '+380100110009');

----------------
delete from products where code = 'S001';
insert into products (CODE, NAME, PRICE)
values ('S001', 'Core Java', 100);

delete from products where code = 'S002';
insert into products (CODE, NAME, PRICE)
values ('S002', 'Spring for Beginners', 50);

delete from products where code = 'S003';
insert into products (CODE, NAME, PRICE)
values ('S003', 'Swift for Beginners', 120);

delete from products where code = 'S004';
insert into products (CODE, NAME, PRICE)
values ('S004', 'Oracle XML Parser', 120);

delete from products where code = 'S005';
insert into products (CODE, NAME, PRICE)
values ('S005', 'CSharp Tutorial for Beginers', 110);

insert into PRODUCT_LOCATION (PRODUCT_ID,CITY,COUNTRY,ADDRES) values ((select PRODUCT_ID from PRODUCTS where CODE = 'S001' ), 'Kiev', 'Ukraine','Shevchenka 14');
insert into PRODUCT_LOCATION (PRODUCT_ID,CITY,COUNTRY,ADDRES) values ((select PRODUCT_ID from PRODUCTS where CODE = 'S002' ), 'Kiev', 'Ukraine','Shevchenka 15');
insert into PRODUCT_LOCATION (PRODUCT_ID,CITY,COUNTRY,ADDRES) values ((select PRODUCT_ID from PRODUCTS where CODE = 'S003' ), 'Kiev', 'Ukraine','Shevchenka 16');
insert into PRODUCT_LOCATION (PRODUCT_ID,CITY,COUNTRY,ADDRES) values ((select PRODUCT_ID from PRODUCTS where CODE = 'S004' ), 'Kiev', 'Ukraine','Shevchenka 17');
insert into PRODUCT_LOCATION (PRODUCT_ID,CITY,COUNTRY,ADDRES) values ((select PRODUCT_ID from PRODUCTS where CODE = 'S005' ), 'Kiev', 'Ukraine','Shevchenka 18');
Commit;
