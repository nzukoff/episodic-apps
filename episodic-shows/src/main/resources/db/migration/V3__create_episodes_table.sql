CREATE TABLE episodes (
  id              BIGINT      NOT NULL    AUTO_INCREMENT,
  show_id         BIGINT      NOT NULL,
  season_number   BIGINT      NOT NULL,
  episode_number  BIGINT      NOT NULL    UNIQUE,
  PRIMARY KEY (id),
  CONSTRAINT `fk_show_id`
  FOREIGN KEY (show_id)
  REFERENCES shows (id)
);
