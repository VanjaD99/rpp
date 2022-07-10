import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Obrazovanje } from 'src/app/models/obrazovanje';
import { Radnik } from 'src/app/models/radnik';
import { Sektor } from 'src/app/models/sektor';
import { ObrazovanjeService } from 'src/app/services/obrazovanje.service';
import { RadnikService } from 'src/app/services/radnik.service';
import { SektorService } from 'src/app/services/sektor.service';

@Component({
  selector: 'app-radnik-dialog',
  templateUrl: './radnik-dialog.component.html',
  styleUrls: ['./radnik-dialog.component.css']
})
export class RadnikDialogComponent implements OnInit {

  public flag: number;
  sektori: Sektor[];
  obrazovanja: Obrazovanje[];

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<RadnikDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Radnik,
    public radnikService: RadnikService,
    public sektorSerivce: SektorService,
    public obrazovanjeService: ObrazovanjeService){ }

  ngOnInit(): void {
    this.sektorSerivce.getAllSektor().subscribe(
      data => {
        this.sektori = data;
      }
    );
    this.obrazovanjeService.getAllObrazovanja().subscribe(
      data => {
        this.obrazovanja = data;
      }
    );
  }
  compareTo(a, b) {
    return a.id == b.id;
  }
  public add(): void {
    this.radnikService.addRadnik(this.data)
      .subscribe(() => {
        this.snackBar.open('Radnik uspešno dodat: ' + this.data.id, 'OK', {
          duration: 2500
        })
      }),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open('Došlo je do greške prilikom dodavanja radnika: ' + this.data.id, 'Zatvori', {
          duration: 2500
        })
      }
  }
  public update(): void {
    this.radnikService.updateRadnik(this.data)
      .subscribe(() => {
        this.snackBar.open('Radnik uspešno izmenjena: ' + this.data.id, 'OK', {
          duration: 2500
        })
      }),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open('Došlo je do greške prilikom izmene radnika: ' + this.data.id, 'Zatvori', {
          duration: 2500
        })
      }
  }
  public delete(): void {
    this.radnikService.deleteRadnik(this.data.id)
      .subscribe(() => {
        this.snackBar.open('Radnik uspešno obrisana: ' + this.data.id, 'OK', {
          duration: 2500
        })
      }),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open('Došlo je do greške prilikom brisanja radnika: ' + this.data.id, 'Zatvori', {
          duration: 2500
        })
      }
  }
  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste.' + this.data.id, 'Zatvori', {
      duration: 1000
    })
  }

}
