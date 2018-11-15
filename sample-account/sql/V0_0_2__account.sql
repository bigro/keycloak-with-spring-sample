CREATE TABLE account.entry_account (
  account_id varchar NOT NULL,
  email varchar NOT NULL,
  confirmation_code char(4) NOT NULL,
  created_at timestamp without time zone DEFAULT now() NOT NULL,
  CONSTRAINT pk_entry_account PRIMARY KEY (account_id)
);
COMMENT ON TABLE  account.entry_account                             IS '申し込みアカウント';
COMMENT ON COLUMN account.entry_account.account_id                  IS 'アカウントID';
COMMENT ON COLUMN account.entry_account.email                       IS 'メールアドレス';
COMMENT ON COLUMN account.entry_account.confirmation_code           IS '確認コード';