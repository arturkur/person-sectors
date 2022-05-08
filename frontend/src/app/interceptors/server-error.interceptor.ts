import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { SnackBarService } from "../modals/message-snackbar/snack-bar.service";

@Injectable({
  providedIn: "root"
})
export class ServerErrorInterceptor implements HttpInterceptor {
  constructor (private snackBarService: SnackBarService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      return next.handle(req).pipe(
        catchError((error: HttpErrorResponse) => {
          if (typeof error.error === "string") {
            this.snackBarService.openSnackBar(error.error, true);
          } else {
            this.snackBarService.openSnackBar("Server error, try again!", true)
          }
          return throwError(() => new Error("Error"));
        })
      )
  }
}
