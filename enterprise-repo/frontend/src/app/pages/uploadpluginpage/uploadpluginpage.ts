import { Component, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MdButtonModule, MdInputModule } from '@angular/material';
import {
    HttpClient, HttpClientModule, HttpClientXsrfModule, HttpErrorResponse,
    HttpEventType, HttpHeaders, HttpRequest
} from '@angular/common/http';

import { FooterModule } from '../../shared/footer';

const API_UPLOAD_FILE = 'api/v1/uploadplugin/';

@Component({
    selector: 'app-uploadplugin',
    templateUrl: './uploadpluginpage.html',
    styleUrls: ['./uploadpluginpage.scss'],
})
export class UploadPluginPageComponent {
    private enabled = false;
    private showError = false;
    private errorMsg = '';


    constructor(private httpClient: HttpClient) { }

    uploadPlugin(file: File) {
        const formData = new FormData();
        formData.append('plugin_file', file, file.name);

        const req = new HttpRequest('POST', API_UPLOAD_FILE, formData, {
            reportProgress: true,
        });

        this.httpClient.request(req).subscribe(
            event => {
                if (event.type == HttpEventType.UploadProgress) {
                    const percentDone = Math.round(100 * event.loaded / event.total);
                    console.log(`File is ${percentDone}% uploaded.`);
                } else if (event.type == HttpEventType.Response) {
                    console.log(`File is completely uploaded!`);
                }
            },
            (err: HttpErrorResponse) => {
                if (err.error instanceof Error) {
                    console.log('client side error');
                } else {
                    console.log(`server returned code: ${err.status}, body: ${err.error}`)
                }
            }
        );
    }

    fileSelectedChanged(inputElem) {
        this.enabled = !!inputElem.value;
        if (this.enabled) {
            // clear error message
            this.errorMsg = '';
        }
        // max allow 100M
        if (this.enabled) {
            if (inputElem.files[0].size > 2 * 1024 * 1024) {
                this.enabled = false;
                this.showError = true;
                this.errorMsg = 'Max plugin size is 100M';
            }
        }
    }
}

@NgModule({
    imports: [
        FooterModule,
        FormsModule,
        ReactiveFormsModule,
        MdInputModule,
        MdButtonModule,
        HttpClientModule,
        HttpClientXsrfModule.withOptions({
            cookieName: 'csrftoken',
            headerName: 'X-CSRFToken',
        }),
    ],
    exports: [UploadPluginPageComponent],
    declarations: [UploadPluginPageComponent],
    providers: [],
})
export class UploadPluginModule { }
