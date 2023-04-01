CREATE SCHEMA IF NOT EXISTS kafka_connect_demo_debezium_postgres;

CREATE TABLE kafka_connect_demo_debezium_postgres.item (
    id uuid NOT NULL,
    name varchar(4096) NOT NULL,
    CONSTRAINT item_pkey PRIMARY KEY (id)
);

CREATE TABLE kafka_connect_demo_debezium_postgres.outbox (
                              id uuid NOT NULL,
                              destination varchar(255) NULL,
                              payload varchar(4096) NULL,
                              timestamp int8 NOT NULL,
                              version varchar(255) NULL,
                              CONSTRAINT outbox_pkey PRIMARY KEY (id)
);

