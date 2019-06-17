insert into campaign_manager (id, username, password, password_change_required) values
(1, 'admin', '$e0808$ziN95PXDH3QmOV+kymcEWBtv4r25q8cUuW1WFi2y3LFoYy44Ic5IFVCvpBL/nmRUhn5LECQqwmAD0P0mMFC8zQ==$f/CqvRzECwDJmryDehkY8r1MlL7N+Gx+FslgeLcn6DGm77AhgZ2iYppMWe9axp9cJEor8UlLhZYa76zjAiAKRg==', 'true');

insert into authority(id, authority) values
(1, 'ROLE_CAMPAIGN_MANAGER');

insert into users_authorities (user_id, authority_id) values
(1, 1);