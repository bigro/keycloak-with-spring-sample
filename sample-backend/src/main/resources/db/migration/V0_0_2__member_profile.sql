CREATE TABLE member.profile (
  account_id varchar NOT NULL,
  nick_name varchar NOT NULL,
  created_at timestamp without time zone DEFAULT now() NOT NULL,
  CONSTRAINT pk_profile PRIMARY KEY (account_id)
);
COMMENT ON TABLE  member.profile                             IS 'メンバープロフィール';
COMMENT ON COLUMN member.profile.nick_name                   IS 'ニックネーム';
