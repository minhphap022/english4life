CREATE TABLE event_publication (
    id UUID not null,
    publication_date timestamp with time zone not null,
    listener_id varchar(512) not null ,
    serialized_event        TEXT                     NOT NULL,
    event_type              VARCHAR(512)             NOT NULL,
    completion_date         TIMESTAMP WITH TIME ZONE,
    last_resubmission_date  TIMESTAMP WITH TIME ZONE,
    completion_attempts     INTEGER                  NOT NULL DEFAULT 0,
    status                  VARCHAR(64),
    PRIMARY KEY (id)
)