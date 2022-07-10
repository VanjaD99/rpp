import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Obrazovanje } from 'src/app/models/obrazovanje';
import { Radnik } from 'src/app/models/radnik';
import { Sektor } from 'src/app/models/sektor';
import { RadnikService } from 'src/app/services/radnik.service';
import { RadnikDialogComponent } from '../dialogs/radnik-dialog/radnik-dialog.component';

@Component({
  selector: 'app-radnik',
  templateUrl: './radnik.component.html',
  styleUrls: ['./radnik.component.css']
})
export class RadnikComponent implements OnInit, OnDestroy, OnChanges {

  displayedColumns = ['id', 'ime', 'prezime','brojLk', 'sektor','obrazovanje', 'actions']; 
  dataSource: MatTableDataSource<Radnik>;
  @Input() selektovanSektor: Sektor;
  subscription: Subscription;
  @ViewChild(MatSort, {static:false}) sort:MatSort;

  constructor(private radnikService: RadnikService,
    private dialog: MatDialog) { }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  ngOnChanges(): void{
    if(this.selektovanSektor.id)
    this.loadDataBySektor();
  }
  ngOnInit(): void {
    //this.loadData();
  }
  loadDataBySektor(){
    this.radnikService.getRadnikBySektor(this.selektovanSektor.id).subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort=this.sort;
    }),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
      }
  }
  
  loadData() {
    this.radnikService.getAllRadnici().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
    }),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
      }
  }

  public openDialog(flag: number, id?: number, ime?:string, prezime?: string, brojLk?: number, sektor?:Sektor, obrazovanje?:Obrazovanje) { 
    const dialogRef = this.dialog.open(RadnikDialogComponent, 
      {data: {
        id,ime,prezime,brojLk,sektor,obrazovanje
      }});
    dialogRef.componentInstance.flag = flag; 
    if(flag===1){
      dialogRef.componentInstance.data.sektor=this.selektovanSektor;
    }
    dialogRef.afterClosed().subscribe(res => {
      if(res === 1)
      {
        dialogRef.componentInstance.data.sektor=this.selektovanSektor;
        this.loadDataBySektor();
      }
      
    })
  }
  selectRow(row: any){
    console.log(row);
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLocaleLowerCase();   
    this.dataSource.filter = filterValue;

  }

}
