import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBarRef, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';

@Component({
  selector: 'app-message-snackbar',
  templateUrl: './message-snackbar.component.html',
  styleUrls: ['./message-snackbar.component.css']
})
export class MessageSnackbarComponent implements OnInit {

  constructor(private sbRef: MatSnackBarRef<MessageSnackbarComponent>,
    @Inject(MAT_SNACK_BAR_DATA) public data: any) { }

  ngOnInit(): void {}

  closeSnackBar() {
    this.sbRef.dismiss();
  }
}
