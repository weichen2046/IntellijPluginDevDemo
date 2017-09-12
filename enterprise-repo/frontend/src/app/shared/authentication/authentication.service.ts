import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { User } from './user';

@Injectable()
export class AuthenticationService {

    private user: User;

    private headers = new Headers({ 'Content-Type': 'application/json' });
    private loginApiUrl = 'api/v1/auth/login/';
    private logoutApiUrl = 'api/v1/auth/logout/';
    private registerApiUrl = 'api/v1/auth/register/';

    constructor(private http: Http) {
        this.loadUserFromCookies().then(user => {
            this.user = user;
        });
    }

    private loadUserFromCookies(): Promise<User> {
        // TODO: load user info from cookies
        return Promise.resolve(null);
    }

    getAuthenticatedUser(): User {
        return this.user;
    }

    isAuthenticated(): boolean {
        return !!this.user;
    }

    setAuthenticatedUser(user: User) {
        this.user = user;
        // TODO: store user info from cookies
    }

    unauthenticate() {
        this.user = null;
        // TODO: clear user info from cookies
    }

    login(username: string, password: string): Promise<User> {
        const data = JSON.stringify({
            'username': username,
            'password': password
        });
        return this.http.post(this.loginApiUrl, data, { headers: this.headers })
            .toPromise()
            .then(resp => {
                this.setAuthenticatedUser(resp.json() as User);
                return this.getAuthenticatedUser();
            })
            .catch(error => {
                return Promise.reject(error);
            });
    }

    logout(): Promise<boolean> {
        return this.http.post(this.logoutApiUrl, {})
            .toPromise()
            .then(resp => {
                this.unauthenticate();
                return true;
            })
            .catch(error => {
                console.log('logout failed:', error);
                this.unauthenticate();
                return Promise.reject(false);
            });
    }

    register(email: string, username: string, password: string): Promise<boolean> {
        console.log('register called, email:', email, 'username:', username, 'pwd:', password);
        const data = JSON.stringify({
            'email': email,
            'username': username,
            'password': password,
        });
        return this.http.post(this.registerApiUrl, data, { headers: this.headers })
            .toPromise()
            .then(resp => {
                return true;
            })
            .catch(error => {
                return false;
            });
    }
}
