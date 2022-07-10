import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AboutComponent } from './components/core/about/about.component';
import { AutorComponent } from './components/core/autor/autor.component';
import { HomeComponent } from './components/core/home/home.component';
import { ObrazovanjeComponent } from './components/obrazovanje/obrazovanje.component';
import { PreduzeceComponent } from './components/preduzece/preduzece.component';
import { RadnikComponent } from './components/radnik/radnik.component';
import { SektorComponent } from './components/sektor/sektor.component';
import { Obrazovanje } from './models/obrazovanje';


const routes: Routes = [
  {path: 'preduzece', component: PreduzeceComponent},
  {path: 'obrazovanje', component: ObrazovanjeComponent},
  //{path: 'radnik', component: RadnikComponent},
  {path: 'sektor',component: SektorComponent},
  {path: 'home', component:HomeComponent},
  {path: 'about', component:AboutComponent},
  {path: 'author', component:AutorComponent},
  {path: '',redirectTo:'/home',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
