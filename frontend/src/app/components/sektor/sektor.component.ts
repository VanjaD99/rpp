import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Preduzece } from 'src/app/models/preduzece';
import { Radnik } from 'src/app/models/radnik';
import { Sektor } from 'src/app/models/sektor';
import { SektorService } from 'src/app/services/sektor.service';
import { SektorDialogComponent } from '../dialogs/sektor-dialog/sektor-dialog.component';

@Component({
  selector: 'app-sektor',
  templateUrl: './sektor.component.html',
  styleUrls: ['./sektor.component.css']
})
export class SektorComponent implements OnInit, OnDestroy {

  displayedColumns = ['id', 'naziv', 'oznaka', 'preduzece', 'actions'];
  dataSource: MatTableDataSource<Sektor>;
  selektovanSektor: Sektor;
  subscription: Subscription;
  @ViewChild(MatSort, {static:false}) sort:MatSort;

  constructor(private sektorService: SektorService,
    private dialog: MatDialog) { }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    
    this.loadData();
  }

  loadData() {
    this.sektorService.getAllSektor().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort=this.sort;
    }),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
        
      }
  }

  public openDialog(flag: number, id?: number, naziv?:string, oznaka?: string, preduzece?:Preduzece) {
    const dialogRef = this.dialog.open(SektorDialogComponent, 
      {data: {
        id,naziv,oznaka,preduzece
      }});
    dialogRef.componentInstance.flag = flag; 
    dialogRef.afterClosed().subscribe(res => {
      if(res === 1)
      {
        this.loadData();
      }
    })
  }
  selectRow(row: any){
    this.selektovanSektor=row;
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLocaleLowerCase();   
    this.dataSource.filter = filterValue;

  }

}
