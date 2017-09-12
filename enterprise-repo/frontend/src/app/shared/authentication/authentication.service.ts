import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { User } from './user';

const LOCAL_USER_ACCESS_KEY = 'localUser';

@Injectable()
export class AuthenticationService {

    private user: User;

    private headers = new Headers({ 'Content-Type': 'application/json' });
    private loginApiUrl = 'api/v1/auth/login/';
    private logoutApiUrl = 'api/v1/auth/logout/';
    private registerApiUrl = 'api/v1/auth/register/';

    // TODO: user new HttpClient api instead of Http
    constructor(private http: Http) {
        this.loadUserFromCookies().then(user => {
            this.user = user;
        });
    }

    private loadUserFromCookies(): Promise<User> {
        // load user info from local storage
        let user: User = null;
        let userStr = localStorage.getItem(LOCAL_USER_ACCESS_KEY);
        if (!!userStr) {
            user = JSON.parse(userStr) as User;
        }
        return Promise.resolve(user);
    }

    getAuthenticatedUser(): User {
        return this.user;
    }

    isAuthenticated(): boolean {
        return !!this.user;
    }

    setAuthenticatedUser(user: User) {
        this.user = user;
        let userStr = JSON.stringify(user);
        // store user info to local storage
        localStorage.setItem(LOCAL_USER_ACCESS_KEY, userStr);
    }

    unauthenticate() {
        this.user = null;
        // clear user info from local storage
        localStorage.removeItem(LOCAL_USER_ACCESS_KEY);
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
            .catch(resp => {
                return Promise.reject(resp);
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
