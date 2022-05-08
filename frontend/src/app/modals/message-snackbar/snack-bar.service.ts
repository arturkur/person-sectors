import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageSnackbarComponent } from './message-snackbar.component';

@Injectable({
  providedIn: 'root'
})
export class SnackBarService {

  constructor(private snackBar: MatSnackBar) { }

  openSnackBar(message: string, isDanger: boolean) {
    const panelClass = isDanger ? "snackbar-danger" : "snackbar-success";
    this.snackBar.openFromComponent(MessageSnackbarComponent, {
      data: message,
      duration: 40000,
      horizontalPosition: "center",
      verticalPosition: "top",
      panelClass: [panelClass]
    });
  }
}
