namespace Epione.Domain
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("epione.appointment")]
    public partial class appointment
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public appointment()
        {
            evaluations = new HashSet<evaluation>();
        }

        public int id { get; set; }

        public DateTime? createdate { get; set; }

        [Column(TypeName = "date")]
        public DateTime? date { get; set; }

        public TimeSpan? endTime { get; set; }

        [StringLength(255)]
        public string message { get; set; }

        public TimeSpan? startTime { get; set; }

        [StringLength(20)]
        public string status { get; set; }

        public int? doctor_id { get; set; }

        public int? medicalRecord_id { get; set; }

        public int? motive_id { get; set; }

        public int? patient_id { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<evaluation> evaluations { get; set; }

        public virtual motive motive { get; set; }

        public virtual user user { get; set; }

        public virtual user user1 { get; set; }

        public virtual medicalrecord medicalrecord { get; set; }
    }
}
