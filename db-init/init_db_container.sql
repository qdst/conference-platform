CREATE ROLE c_room_user     LOGIN PASSWORD 'c_room_user';
CREATE ROLE c_control_user  LOGIN PASSWORD 'c_control_user';
CREATE ROLE c_feedback_user LOGIN PASSWORD 'c_feedback_user';

CREATE SCHEMA c_room     AUTHORIZATION c_room_user;
CREATE SCHEMA c_control  AUTHORIZATION c_control_user;
CREATE SCHEMA c_feedback AUTHORIZATION c_feedback_user;

GRANT ALL ON SCHEMA c_room     TO c_room_user;
GRANT ALL ON SCHEMA c_control  TO c_control_user;
GRANT ALL ON SCHEMA c_feedback TO c_feedback_user;

ALTER DEFAULT PRIVILEGES FOR ROLE conference_room_db_user IN SCHEMA c_room
  GRANT ALL ON TABLES, SEQUENCES, FUNCTIONS TO c_room_user;
ALTER DEFAULT PRIVILEGES FOR ROLE conference_room_db_user IN SCHEMA c_control
  GRANT ALL ON TABLES, SEQUENCES, FUNCTIONS TO c_control_user;
ALTER DEFAULT PRIVILEGES FOR ROLE conference_room_db_user IN SCHEMA c_feedback
  GRANT ALL ON TABLES, SEQUENCES, FUNCTIONS TO c_feedback_user;
