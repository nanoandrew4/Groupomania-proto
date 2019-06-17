INSERT INTO campaign_manager (id, username, password, password_change_required) values
(1, 'admin', '$e0808$9AIqgGLBKYIHGAn/kk6CROlmpgAB/Gpe1ITmeU9snVFRsdBiqylt3vGS48v9e4J/TB866paM0aaV+zjEFJKwlg==$izKF4OSu7X9ClYk177lfWJJaW60rj0OMcOSXlczXCyw9hcz9baWWWm3wsSBx141YerpEwQ/U/UXazW78GlEUfA==', 'true');

insert into authority(id, authority) values
(1, 'ROLE_CAMPAIGN_MANAGER');

insert into users_authorities (user_id, authority_id) values
(1, 1);