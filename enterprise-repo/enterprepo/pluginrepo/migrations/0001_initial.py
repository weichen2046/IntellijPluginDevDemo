# -*- coding: utf-8 -*-
# Generated by Django 1.11.4 on 2017-09-05 10:45
from __future__ import unicode_literals

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Plugin',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('strId', models.CharField(max_length=256)),
                ('url', models.CharField(max_length=2083)),
                ('version', models.CharField(max_length=50)),
                ('createTime', models.DateTimeField(auto_now_add=True)),
                ('updateTime', models.DateTimeField(auto_now=True)),
                ('author', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='plugins', to=settings.AUTH_USER_MODEL)),
                ('lastUpdater', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='+', to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]